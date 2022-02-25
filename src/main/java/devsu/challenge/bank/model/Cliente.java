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
@Table(name = "CLIENTE")
@Getter
@Setter
public class Cliente {
	
	public static final int CODE_MAX_SIZE = 13;
	public static final int NAME_MAX_SIZE = 50;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_CLIENTE")
	private Long idCliente;
	
	@Column(name = "IDENTIFICACION", nullable = false, length = CODE_MAX_SIZE)
	private String identificacion;
	
	@Column(name = "NOMBRE", nullable = false, length = NAME_MAX_SIZE)
	private String nombre;
	
	@Column(name = "URL_FOTO", nullable = false)
	private String urlFoto;
	
}
