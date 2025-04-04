package org.boardtask.app.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "board_column")
public class BoardColumnEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(name = "`order`", nullable = false)
    private int order;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BoardColumnKinEnum kind;
    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private BoardEntity board;
    @OneToMany(mappedBy = "boardColumn", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CardEntity> card;

    public BoardColumnEntity(String name, int order, BoardColumnKinEnum kind) {
        this.name = name;
        this.order = order;
        this.kind = kind;
    }
}
