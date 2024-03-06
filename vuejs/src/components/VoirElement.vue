<template>
  <div class="voir-element">
    <h2>Visualisation : {{ this.el.type }}</h2>
    <p v-for="(value, propertyName) in this.el" v-bind:key="propertyName">
      <strong>{{ propertyName }}</strong> : {{ value }}
    </p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "VoirElement",
  props: {
    element: Object,
  },
  data() {
    return {
      el: {},
    };
  },
  mounted() {
    let i = 0;
    for (const [key, value] of Object.entries(this.element)) {
      // On récupère le type de l'élément et les propriétés (!= idForeingKey)
      if (i == 0) {
        this.el.type = key.substring(2).toLowerCase();
      } else if (!key.startsWith("id")) {
        this.el[key] = value;
      }
      i++;
    }

    if (this.el.type === "membre") {
      // affiche les artistes du groupe
      let url = "http://localhost:8082/api/v1/membre/lister";
      axios.get(url).catch((error) => {
        console.log(error);
      });
    }
  },
};
</script>
