<template>
  <v-app>
    <br>
    <v-app-bar app dark>
      <div class="d-flex align-center mr-2" style="color:lightgreen;">
        Comisaría Virtual
      </div>

      <v-btn to="/agregarPermiso" text>
        Solicitar permiso
      </v-btn>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "app",
};
</script>