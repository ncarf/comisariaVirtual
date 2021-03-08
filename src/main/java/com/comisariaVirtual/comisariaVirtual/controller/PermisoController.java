package com.comisariaVirtual.comisariaVirtual.controller;

import java.util.Date;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comisariaVirtual.comisariaVirtual.model.Permiso;
import com.comisariaVirtual.comisariaVirtual.repository.PermisoRepository;

import javax.mail.*;
import javax.mail.internet.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class PermisoController {

	@Autowired
	PermisoRepository permisoRepository;

	@PostMapping("/crearPermiso")
	public ResponseEntity<Permiso> createPermiso(@RequestBody Permiso permiso) {
		try {
			Permiso _permiso = permisoRepository
					.save(new Permiso(permiso.getRut(), permiso.getNombre(),permiso.getDireccion(), permiso.getEmail(), permiso.getMotivo(), permiso.getFechaHoraInicio(), permiso.getFechaHoraFin()));

			String mensaje = "Señor(a) " + _permiso.getNombre() + ",\n\n" +
					"Este es un mensaje de prueba de la plataforma de Comisaría Virtual, realizada como parte del ramo Sistemas Distribuidos, de la carrera Ingeniería Informática 2020-II de la Universidad de Santiago de Chile.\n\n" +
					"Ha solicitado un permiso de Comisaría Virtual. Los datos del permiso son los siguientes:\n\n" +
					"ID del permiso: " + _permiso.getId() + "\n" +
					"Motivo del permiso: " + _permiso.getMotivo() + "\n" +
					"RUN/DNI del solicitante: " + _permiso.getRut() + "\n" +
					"Nombre del solicitante: " + _permiso.getNombre() + "\n" +
					"Dirección del solicitante: " + _permiso.getDireccion() + "\n" +
					"Fecha y hora de inicio del permiso: " + _permiso.getFechaHoraInicio() + "\n" +
					"Fecha y hora de fin del permiso: " + _permiso.getFechaHoraFin() + "\n\n" +
					"Saludos cordiales,\n" +
					"Estudiantes USACH";

			sendmailVerificacion(_permiso.getEmail(), mensaje);
			return new ResponseEntity<>(_permiso, HttpStatus.CREATED);
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	private void sendmailVerificacion(String correoDestino, String mensaje) throws MessagingException {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("juanlopezrodriguezprado@gmail.com", "prueba21usach40");
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress("juanlopezrodriguezprado@gmail.com", false));

		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(correoDestino));
		msg.setSubject("Sistemas distribuidos USACH - Prueba plataforma Comisaría Virtual");
		msg.setSentDate(new Date());

		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(mensaje, "text/plain");

		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		msg.setContent(multipart);
		Transport.send(msg);
	}
}