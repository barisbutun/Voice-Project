import os
import subprocess
import librosa
import numpy as np
import soundfile as sf
from pydub import AudioSegment
def convert_mp3_to_wav(mp3_path):
    wav_path = mp3_path.replace(".mp3",".wav")
    if not os.path.exists(wav_path):
        subprocess.run(["ffmpeg","-i",mp3_path,wav_path,"-y"],
                       stdout=subprocess.DEVNULL,stderr=subprocess.DEVNULL)
    return wav_path

def load_audio_file(file_path):
    try:
        y,sr=librosa.load(file_path,sr=44100,mono=True)
        if len(y)==0:
            raise ValueError("Librosa ile yüklenen ses dosyası boş")
        print(f"Librosa: {sr} Hz, {len(y)} örnek yüklendi.")
    except Exception:
        print("Librosa yüklemesi başarısız, Soundfile deneniyor")
        y,sr=sf.read(file_path)
        if len(y)==0:
            raise ValueError("Soundfile ile yüklenen ses dosyası boş")
        print(f"Soundfile:{sr} Hz, {len(y)} örnek yüklendi.")
    return y,sr
def degisim_noktalari_spectral_flux(y,sr):
    onset_env=librosa.onset.onset_strength(y=y,sr=sr)
    onset_frames=librosa.onset.onset_detect(onset_envelope=onset_env,sr=sr,backtrack=True)
    onset_times=librosa.frames_to_time(onset_frames,sr=sr)
    return onset_times.tolist()
def segmentleri_olustur_5sn(degisim_noktalar,total_length,min_sure=1.0,max_sure=5.0):
    segmentler=[]
    baslangic=0.0
    for nokta in degisim_noktalar:
        while(nokta-baslangic)>max_sure:
            segmentler.append((baslangic,baslangic+max_sure))
            baslangic+=max_sure
        if(nokta-baslangic)<min_sure:
            continue
        else:
            segmentler.append((baslangic,nokta))
            baslangic=nokta
    while(total_length-baslangic)>max_sure:
        segmentler.append((baslangic,baslangic+max_sure))
        baslangic+=max_sure
    leftover=total_length-baslangic
    if leftover>0:
        if leftover<min_sure and segmentler:
            last_seg_start,last_seg_end=segmentler[-1]
            segmentler[-1]=(last_seg_start,total_length)
        else:
            segmentler.append((baslangic,total_length))
    return segmentler
def format_sure(saniye):
    saat=int(saniye//3600)
    dakika=int((saniye % 3600)//60)
    saniye_remainder=saniye%60
    return f"{saat:02}:{dakika:02}:{saniye_remainder:04.1f}"
def segmentleri_kaydet(segmentler,output_file="beat_segment.txt"):
    with open(output_file,"w") as f:
        for start,end in segmentler:
            f.write(f"{format_sure(start)}-{format_sure(end)}\n")
    print(f" Segmentler {output_file} dosyasına kaydedildi")
def segmentleri_ses_dosyalarina_ayir(segmentler,wav_path,output_dir):
    """
    Tespit edilen segmentlere göre orijinal WAV dosyasını parçalara ayırır.
    Her segmenti output_dir klasöründe ayrı WAV dosyası olarak kaydeder.
    """
    if not os.path.exists(output_dir):
        os.makedirs(output_dir)
    original_audio=AudioSegment.from_wav(wav_path)
    for i, (start_sec,end_sec) in enumerate(segmentler,start=1):
        start_ms=int(start_sec*1000)
        end_ms=int(end_sec*1000)
        segment_audio=original_audio[start_ms:end_ms]
        segment_file=os.path.join(output_dir,f"segment_{i}.wav")
        segment_audio.export(segment_file,format="wav")
        print(f"Segment {i}: {start_sec:.2f} - {end_sec:.2f} sn -> {segment_file}")
def muzik_isle(file_path):
    # MP3  WAV dönüştürme
    wav_path=convert_mp3_to_wav(file_path)
    # Ses dosyasını yükle
    y,sr=load_audio_file(wav_path)
    total_length=len(y)/sr
    print(f"Toplam süre: {total_length:.2f} sn")
    # Değişim noktalarını tespit et
    degisim_noktalar=degisim_noktalari_spectral_flux(y,sr)
    print(" Tespit edilen değişim noktaları (s):",degisim_noktalar)
    # Segmentleri oluştur (min 1 sn, max 5 sn)
    segmentler = segmentleri_olustur_5sn(degisim_noktalar,total_length,min_sure=1.0,max_sure=5.0)
    print("Oluşturulan segmentler:",segmentler)
    # Segment bilgilerini txt dosyasına kaydet
    segmentleri_kaydet(segmentler)
    # Orijinal müziğin adını klasör ismi olarak kullan
    base_name=os.path.splitext(os.path.basename(file_path))[0]
    output_dir=base_name  # Örneğin: "DISSTrack"
    # Segmentlere göre müziği parçalara ayırıp, klasöre kaydet
    segmentleri_ses_dosyalarina_ayir(segmentler,wav_path,output_dir)
if __name__ == "__main__":
    dosya_yolu="DISSTrack.mp3"  # İşlenecek müzik dosyası
    muzik_isle(dosya_yolu)
