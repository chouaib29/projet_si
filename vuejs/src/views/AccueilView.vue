<template>
  <div class="accueil">
    <img alt="Logo Event" src="@/assets/event.png" />
    <Presentation titre="Evenement" />
    <div class="hello">
      <p>Bienvenue</p>
      <h1 id="titre-accueil">GESTION EVENEMENT</h1>
      <p>Chouaib <strong>Daili</strong> / Moetez <strong>Chouikha</strong></p>
      <p>Anas <strong>Fouad</strong> / Mohamed Amine <strong>EL MOH</strong></p>
    </div>
  </div>
</template>

<script>
export default {
  name: "AccueilView",
};
</script>

<style scoped lang="scss">
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #218838;
}
#titre-accueil {
  color: #218838;
}
td {
  max-width: 200px;
}
</style>
