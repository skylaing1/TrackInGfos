package org.example.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "token")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private int tokenId;

    @Column(name = "token_content")
    private String token_content;

    @Column(name = "token_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date token_timestamp;

    @ManyToOne
    @JoinColumn(name = "credentials_id", referencedColumnName = "credentials_id")
    private LoginData loginData;

    //Getters und Setters

    public int getTokenId() {return tokenId;}
    public void setTokenId(int tokenId) {this.tokenId = tokenId;}

    public String getToken_content() {return token_content;}
    public void setToken_content(String tokenContent) {
        this.token_content = tokenContent;
    }

    public Date getTimestamp() {
        return token_timestamp;
    }
    public void setToken_timestamp() {
        this.token_timestamp = new Date();
    }

    public LoginData getLoginData() {
        return loginData;
    }
    public void setLoginData(LoginData loginDataByEmail) {
        this.loginData = loginDataByEmail;
    }
}