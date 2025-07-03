package com.github.guiziin227.livraria.model.PK;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class VendaPK implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @Column(name = "livro_id", nullable = false)
    private Long livroId;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        VendaPK vendaPK = (VendaPK) o;
        return Objects.equals(clienteId, vendaPK.clienteId) && Objects.equals(livroId, vendaPK.livroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clienteId, livroId);
    }
}
