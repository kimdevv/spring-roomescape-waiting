package roomescape.member.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import roomescape.global.exception.AlreadyEntityException;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 30)
    private String name;
    @Column(length = 100)
    private String email;
    @Column(length = 100)
    private String password;
    @Enumerated(value = EnumType.STRING)
    @Column(length = 10)
    private Role role;

    protected Member() {

    }

    public Member(Long id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static Member generateNormalMember(String name, String email, String password) {
        return new Member(null, name, email, password, Role.NORMAL);
    }

    public Member toEntity(Long id) {
        if (this.id == null) {
            return new Member(id, name, email, password, role);
        }
        throw new AlreadyEntityException("해당 멤버는 이미 엔티티화 된 상태입니다.");
    }

    public boolean isAdmin() {
        return role.isAdmin();
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
