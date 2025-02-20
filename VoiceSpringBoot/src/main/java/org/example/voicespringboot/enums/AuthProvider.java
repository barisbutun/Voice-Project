package org.example.voicespringboot.enums;

public enum AuthProvider {
    GOOGLE("https://accounts.google.com");

    private final String issuerUrl;

    AuthProvider(String issuerUrl) {
        this.issuerUrl = issuerUrl;
    }

    public String getIssuerUrl(){
        return issuerUrl;
    }

    public static AuthProvider fromIssuer(String issuer) {
        for (AuthProvider provider : AuthProvider.values()) {
            if (provider.getIssuerUrl().equals(issuer)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown OAuth provider: " + issuer);
    }


}
