package org.techtown.club.sendServerData;

public class IdTokenObject {
    private String idToken;

    public IdTokenObject(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @Override
    public String toString() {
        return "IdTokenObject{" +
                "idToken='" + idToken + '\'' +
                '}';
    }
}
