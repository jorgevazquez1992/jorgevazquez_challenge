package devsu.challenge.bank.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedido_cabecera")
@Getter
@Setter
public class PedidoCabecera {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_pedido_cabecera", nullable = false)
	private Long idPedidoCabecera;
	
	@ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
	private Cliente cliente;
	
	@Column(name = "fecha_pedido", nullable = false)
	private Date fechaPedido;
	
	@OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "pedidoCabecera")
    @Setter(AccessLevel.PRIVATE)
	private List<PedidoDetalle> detalles = new ArrayList<>();
	
	public PedidoCabecera(Cliente cliente) {
		this.setCliente(cliente);
	}
	
	public List<PedidoDetalle> getDetalles(){
		return List.copyOf(this.detalles);
	}
	
	public void addDetalles(PedidoDetalle pedidoDetalle) {
		detalles.add(pedidoDetalle);
	}
	
}
