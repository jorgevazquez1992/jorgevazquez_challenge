package devsu.challenge.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pedido_detalle")
@Getter
@Setter
public class PedidoDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_pedido_detalle", nullable = false)
	private Long idPedidoDetalle;
	
	@ManyToOne
    @JoinColumn(name = "id_pedido_cabecera", nullable = false)
	private PedidoCabecera pedidoCabecera;
	
	@ManyToOne
    @JoinColumn(name = "id_producto_por_tienda", nullable = false)
	private ProductoPorTienda productoPorTienda;

	@Column(name = "cantidad", nullable = false)
	private Integer cantidad;
	
}
