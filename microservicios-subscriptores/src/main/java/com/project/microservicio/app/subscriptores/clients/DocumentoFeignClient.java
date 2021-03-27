package com.project.microservicio.app.subscriptores.clients;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import feign.Headers;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;


@FeignClient(name = "microservicio-documentos")
public interface DocumentoFeignClient {
	
	@Configuration
    class MultipartSupportConfig {

        @Autowired
        private ObjectFactory<HttpMessageConverters> messageConverters;

        @Bean
        @Primary
        @Scope("prototype")
        public Encoder feignFormEncoder() {
            return new SpringFormEncoder(new SpringEncoder(messageConverters));
        }

    }
	
	@PostMapping(path="/documentos-rest/foto/{subscriptorId}", consumes = {"multipart/form-data"})
	@Headers("Content-Type: multipart/form-data")
	public String guardar(@PathVariable Long subscriptorId, @RequestPart MultipartFile archivo);
	
	@PutMapping(path="/foto/{subscriptorId}", consumes = {"multipart/form-data"})
	@Headers("Content-Type: multipart/form-data")
	public String editar(@PathVariable Long subscriptorId, @RequestPart MultipartFile archivo);
	
	@GetMapping("/documentos-rest/foto/subscriptor/{id}")
	public ByteArrayResource obtenerFotoBySubscriptorId(@PathVariable Long id);
	
	@DeleteMapping("/documentos-rest/foto/subscriptor/{id}")
	public void eliminarFotoBySubscriptorId(@PathVariable Long id);
	
	@DeleteMapping("/documentos-rest/foto/empleado/{id}")
	public void eliminarFotoByEmpleadoId(@PathVariable Long id);
}
