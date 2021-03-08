<template>
  <div class="submit-form mt-3 mx-auto">
    <p class="headline">Solicitar permiso</p>

    <div v-if="!enviado">
      <v-form ref="form" smart-validation v-model="valid">
        <v-text-field
          v-model="permiso.rut"
          :rules="[reglas.requerido, reglas.reglaRut]"
          label="RUN/DNI"
          required
        ></v-text-field>

        <v-text-field
          v-model="permiso.nombre"
          :rules="[reglas.requerido, reglas.reglaNombre]"
          type="text"
          label="Nombre"
          required
        ></v-text-field>

        <v-text-field
          v-model="permiso.direccion"
          :rules="[reglas.requerido]"
          label="Direccion"
          required
        ></v-text-field>

        <v-text-field
          v-model="permiso.email"
          :rules="[reglas.requerido, reglas.reglaEmail]"
          label="Email"
          required
        ></v-text-field
        ><br />

        <v-select
          v-model="permiso.motivo"
          :rules="[reglas.requerido]"
          :items="motivos"
          label="Motivo"
          required
        ></v-select
        ><br />

        <v-text-field
          v-model="permiso.fechaHoraActual"
          label="Fecha y hora actuales"
          required
          readonly
        ></v-text-field>
      </v-form>

      <div class="center">
        <v-btn
          color="primary"
          class="mt-3"
          :disabled="!valid"
          @click="guardarPermiso"
        >
          {{ this.botonSolicitud }}
        </v-btn>
      </div>
      <br /><br /><br /><br /><br /><br />
    </div>

    <div v-else>
      <v-card class="mx-auto">
        <v-card-title>
          Permiso generado exitosamente
        </v-card-title>

        <v-card-subtitle>
          <br />
          <b>ID permiso:</b> {{ this.permiso.id }}<br />
          <b>Motivo permiso:</b> {{ this.permiso.motivo }}<br />
          <b>RUT solicitante:</b> {{ this.permiso.rut }}<br />
          <b>Nombre solicitante:</b> {{ this.permiso.nombre }}<br />
          <b>Direccion solicitante:</b> {{ this.permiso.direccion }}<br />
          <b>Email solicitante:</b> {{ this.permiso.email }}<br />
          <b>Fecha y hora inicio vigencia permiso:</b>
          {{ this.permiso.fechaHoraInicio }}<br />
          <b>Fecha y hora fin vigencia permiso:</b>
          {{ this.permiso.fechaHoraFin }}<br /><br />
          <b>Aprete el botón para solicitar un nuevo permiso.</b>
        </v-card-subtitle>

        <v-card-actions>
          <v-btn color="success" @click="nuevoPermiso">
            Solicitar nuevo permiso
          </v-btn>
        </v-card-actions>
      </v-card>
    </div>
  </div>
</template>

<script>
import moment from "moment";
import axios from "axios";

export default {
  name: "agregar-permiso",
  data() {
    return {
      valid: false,
      botonSolicitud: "Solicitar permiso",
      permiso: {
        id: null,
        rut: "",
        nombre: "",
        direccion: "",
        email: "",
        motivo: "",
        fechaHoraActual: "",
        fechaHoraInicio: "",
        fechaHoraFin: "",
      },
      enviado: false,
      motivos: [
        "Asistencia a establecimiento salud",
        "Compra insumos básicos",
        "Salida personas discapacitadas",
        "Paseo de mascotas",
      ],
      reglas: {
        requerido: (v) => !!v || " Campo obligatorio.",
        reglaRut: (v) =>
          /^[0-9]+[-|‐]{1}[0-9kK]{1}$/.test(v) ||
          "Ingrese RUT sin puntos y con guión",
        reglaNombre: (v) => /^[a-zA-Z]/.test(v) || "Ingrese un nombre válido",
        reglaEmail: (v) =>
          /.+@.+\..+/.test(v) || "Ingrese un correo electrónico válido.",
      },
      reloj: "",
    };
  },
  methods: {
    guardarPermiso() {
      for (var llave in this.permiso) {
        if (this.permiso[llave] === "") {
          return;
        }
      }

      this.botonSolicitud = "Cargando solicitud";

      var data = {
        rut: this.permiso.rut,
        nombre: this.permiso.nombre,
        direccion: this.permiso.direccion,
        email: this.permiso.email,
        motivo: this.permiso.motivo,
        fechaHoraInicio: this.permiso.fechaHoraInicio,
        fechaHoraFin: this.permiso.fechaHoraFin,
      };

      axios
        .post("http://172.24.0.4:8080/crearPermiso", data)
        .then((response) => {
          this.permiso.id = response.data.id;
          this.enviado = true;
        })
        .catch((error) => {
          if (!error.response) {
            // network error
            this.errorStatus = "Error: Network Error";
          } else {
            this.errorStatus = error.response.data.message;
          }
        });
    },

    nuevoPermiso() {
      this.permiso.id = null;
      this.enviado = false;
      this.permiso = {};
      this.obtenerFechaHora();
      this.botonSolicitud = "Solicitar permiso";
    },

    obtenerFechaHora() {
      var fechaHoraActual = new Date();
      var fechaHoraInicio = moment(fechaHoraActual)
        .add(15, "m")
        .toDate();
      var fechaHoraFin = moment(fechaHoraInicio)
        .add(180, "m")
        .toDate();
      this.permiso.fechaHoraActual = moment(String(fechaHoraActual)).format(
        "DD/MM/YYYY HH:mm"
      );
      this.permiso.fechaHoraInicio = moment(String(fechaHoraInicio)).format(
        "DD/MM/YYYY HH:mm"
      );
      this.permiso.fechaHoraFin = moment(String(fechaHoraFin)).format(
        "DD/MM/YYYY HH:mm"
      );
    },
  },

  created() {
    this.obtenerFechaHora();
  },
};
</script>

<style>
.submit-form {
  max-width: 500px;
}
.center {
  display: table;
  margin-right: auto;
  margin-left: auto;
}
</style>
