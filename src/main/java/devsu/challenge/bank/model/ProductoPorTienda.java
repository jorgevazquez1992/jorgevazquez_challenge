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
@Table(name = "producto_por_tienda")
@Getter
@Setter
public class ProductoPorTienda {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_producto_por_tienda")
	private Long idProductoPorTienda;
	
	@ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
	private Producto producto;
	
	@ManyToOne
    @JoinColumn(name = "id_tienda", nullable = false)
	private Tienda tienda;
	
}
