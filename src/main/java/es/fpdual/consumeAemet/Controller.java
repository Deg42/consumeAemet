package es.fpdual.consumeAemet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/aemet")
@RestController
public class Controller {

	@Autowired
	Service service;

	@GetMapping("/{codMunicipio}")
	public ResponseEntity<?> getDatosDeMunicipio(@PathVariable String codMunicipio) {
		DatosAemet datos = this.service.getDatos(codMunicipio);

		return new ResponseEntity<>(datos, HttpStatus.OK);
	}

}
