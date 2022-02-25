package devsu.challenge.bank.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;

@Entity
@Table(name = "PRODUCTO")
@Getter
@Setter
@NoArgsConstructor
public class Producto {

	public static final int CODE_MAX_SIZE = 10;
	public static final int NAME_MAX_SIZE = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PRODUCTO")
	private Long idProducto;

	@Column(name = "CODIGO", nullable = false, length = CODE_MAX_SIZE)
	private String codigo;

	@Column(name = "NOMBRE", nullable = false, length = NAME_MAX_SIZE)
	private String nombre;

	@Column(name = "PRECIO", nullable = false, precision = 18, scale = 6)
	private Float precio;
	
	@Column(name = "STOCK", nullable = false)
	private Integer stock;

	@Singular
	@OneToMany(cascade = CascadeType.ALL,
            orphanRemoval = true, mappedBy = "producto")
    @Setter(AccessLevel.PRIVATE)
    private List<ProductoPorTienda> productosPorTienda = new ArrayList<>();
	
	public Producto(String codigo, String nombre, Float precio, Integer stock) {
		this.setCodigo(codigo);
		this.setNombre(nombre);
		this.setPrecio(precio);
		this.setStock(stock);
	}
	
	public List<ProductoPorTienda> getProductosPorTienda(){
		return List.copyOf(this.productosPorTienda);
	}
	
	public void addProductosPorTienda(ProductoPorTienda productoPorTienda) {
		productosPorTienda.add(productoPorTienda);
	}
	
}
