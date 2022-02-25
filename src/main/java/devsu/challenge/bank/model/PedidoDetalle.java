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
@Table(name = "PEDIDO_DETALLE")
@Getter
@Setter
public class PedidoDetalle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PEDIDO_DETALLE", nullable = false)
	private Long idPedidoDetalle;
	
	@ManyToOne
    @JoinColumn(name = "ID_PEDIDO_CABECERA", nullable = false)
	private PedidoCabecera pedidoCabecera;
	
	@ManyToOne
    @JoinColumn(name = "ID_PRODUCTO_POR_TIENDA", nullable = false)
	private ProductoPorTienda productoPorTienda;

	@Column(name = "CANTIDAD", nullable = false)
	private Integer cantidad;
	
}
