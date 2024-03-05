<template>
  <div class="accueil">
    <img alt="Logo Event" src="@/assets/event.png" />
    <Presentation titre="Evenement" />
  </div>
</template>

<script>
// @ is an alias to /src
import Presentation from "@/components/Presentation.vue";

export default {
  name: "AccueilView",
  components: {
    Presentation,
  },
};
</script>
