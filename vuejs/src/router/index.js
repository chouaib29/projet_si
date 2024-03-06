import { createRouter, createWebHistory } from "vue-router";
import AccueilView from "../views/AccueilView.vue";
import MembreView from "../views/MembreView.vue";
import EvenementView from "@/views/EvenementView.vue";
import LieuView from "@/views/LieuView.vue";

const routes = [
  {
    path: "/",
    name: "accueil",
    component: AccueilView,
  },
  {
    path: "/membres",
    name: "membres",
    component: MembreView,
  },
  {
    path: "/evenements",
    name: "evenements",
    component: EvenementView,
  },
  {
    path: "/lieux",
    name: "lieux",
    component: LieuView,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
