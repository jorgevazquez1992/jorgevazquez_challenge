package devsu.challenge.bank.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente {
	
	public static final int CODE_MAX_SIZE = 13;
	public static final int NAME_MAX_SIZE = 50;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_cliente")
	private Long idCliente;
	
	@Column(name = "identificacion", nullable = false, length = CODE_MAX_SIZE)
	private String identificacion;
	
	@Column(name = "nombre", nullable = false, length = NAME_MAX_SIZE)
	private String nombre;
	
	@Column(name = "url_foto", nullable = false)
	private String urlFoto;
	
}
