package devsu.challenge.bank.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import devsu.challenge.bank.dto.MontoDTO;
import devsu.challenge.bank.dto.NumeroTransaccionesDTO;
import devsu.challenge.bank.dto.PedidoCabeceraDTO;
import devsu.challenge.bank.dto.PedidoDetalleDTO;
import devsu.challenge.bank.dto.TransaccionesClienteDTO;
import devsu.challenge.bank.model.PedidoDetalle;
import devsu.challenge.bank.service.PedidoCabeceraService;

@RestController
@RequestMapping("/pedidoCabecera")
public class PedidoCabeceraController {

	private PedidoCabeceraService pedidoCabeceraService;
	
	@Autowired
	public PedidoCabeceraController(PedidoCabeceraService pedidoCabeceraService) {
		this.pedidoCabeceraService = pedidoCabeceraService;
	}
	
	@PostMapping
	public ResponseEntity<PedidoCabeceraDTO> crear(@RequestBody @Valid PedidoCabeceraDTO pedidoCabeceraDTO) {
		PedidoCabeceraDTO respuesta = pedidoCabeceraService.guardarPedido(pedidoCabeceraDTO);
		return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/transaccionesPorTienda/{idTienda}")
	public List<PedidoDetalleDTO> listarPorTienda(@PathVariable("idTienda") Long idTienda){
		return pedidoCabeceraService.transaccionesPorTienda(idTienda);
	}
	
	@GetMapping(value = "/transaccionesAgrupadas")
	public List<NumeroTransaccionesDTO> transaccionesAgrupadas(){
		return pedidoCabeceraService.transacciones();
	}
	
	@GetMapping(value = "/montoVendido")
	public List<MontoDTO> montoVendido(){
		return pedidoCabeceraService.montoPorProductoTienda();
	}	

	@PostMapping(value = "/reportePorCliente")
    public void exportToCSV(HttpServletResponse response, @RequestBody @Valid TransaccionesClienteDTO dto) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());
         
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
         
        List<PedidoDetalle> detalles = pedidoCabeceraService.buscarPorClienteRango(dto);
 
        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
        String[] csvHeader = {"Identificacion", "Nombre", "Tienda", "Producto", "Cantidad", "Precio"};
        String[] nameMapping = {"pedidoCabecera.cliente.identificacion", "pedidoCabecera.cliente.nombre", "productoPorTienda.tienda.nombre", "productoPorTienda.producto.nombre", "cantidad","productoPorTienda.producto.precio"};
         
        csvWriter.writeHeader(csvHeader);
         
        for (PedidoDetalle detalle : detalles) {
            csvWriter.write(detalle, nameMapping);
        }
        csvWriter.close();
    }
	
}
