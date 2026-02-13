package br.com.saudepraja.domain.model.order;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "service_guide")
public class ServiceGuide {

    private static final SecureRandom RANDOM = new SecureRandom();
    private static final char[] ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789".toCharArray();

    public ServiceGuide(String id, Long createdBy, Scheduling scheduling) {
        this.id = id;
        this.createdBy = createdBy;
        this.scheduling = scheduling;
    }

    @Id
    @Column(name = "service_guide_id", length = 10)
    private String id;

    @Column(name = "dat_verification")
    private LocalDateTime verifiedAt;

    @Column(name = "verified_by_user_id")
    private Long verifiedBy;

    @Column(name = "dat_creation")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "created_by_user_id")
    private Long createdBy;

    @OneToOne(mappedBy = "serviceGuide")
    private Scheduling scheduling;

    public static String generateCode(int length) {
        char[] buf = new char[length];
        for(int x = 0; x < length; x++) {
            buf[x] = ALPHABET[RANDOM.nextInt(ALPHABET.length)];
        }
        return new String(buf);
    }

}
