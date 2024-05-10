package hu.pazmany.dto;

public class DetailedDogDTO extends DogDTO{
    public DetailedDogDTO(Integer id, String name, byte[] picture, Integer age, String breed) {
        super(id, name, picture);
        this.age = age;
        this.breed = breed;
    }

    private Integer age;

    private String breed;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }
}
