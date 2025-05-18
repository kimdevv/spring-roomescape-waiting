package roomescape.reservation.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import roomescape.global.exception.AlreadyEntityException;

@Entity
public class Theme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String name;
    @Column(length = 300)
    private String description;
    @Column(length = 1000)
    private String thumbnail;

    protected Theme() {

    }

    public Theme(Long id, String name, String description, String thumbnail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    public Theme toEntity(Long id) {
        if (this.id == null) {
            return new Theme(id, name, description, thumbnail);
        }
        throw new AlreadyEntityException("해당 테마는 이미 엔티티화 된 상태입니다.");
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
