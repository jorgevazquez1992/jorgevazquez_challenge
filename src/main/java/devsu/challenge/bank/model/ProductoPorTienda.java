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
@Table(name = "PRODUCTO_POR_TIENDA")
@Getter
@Setter
public class ProductoPorTienda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PRODUCTO_POR_TIENDA")
	private Long idProductoPorTienda;
	
	@ManyToOne
    @JoinColumn(name = "ID_PRODUCTO", nullable = false)
	private Producto producto;
	
	@ManyToOne
    @JoinColumn(name = "ID_TIENDA", nullable = false)
	private Tienda tienda;
	
}
