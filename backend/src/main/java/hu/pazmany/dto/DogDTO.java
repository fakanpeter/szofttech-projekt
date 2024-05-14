package hu.pazmany.dto;

public class DogDTO {
    private Integer id;
    private String name;
    private byte[] picture;

    public DogDTO(Integer id, String name, byte[] picture) {
        this.id = id;
        this.name = name;
        this.picture = picture;
    }

    public DogDTO(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
