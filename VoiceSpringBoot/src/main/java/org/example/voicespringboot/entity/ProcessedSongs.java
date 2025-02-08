package org.example.voicespringboot.entity;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="processed_songs")
@ToString
public class ProcessedSongs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "url",unique = true)
    private String url;

    @Column(name = "song_name")
    private String songName;
}
