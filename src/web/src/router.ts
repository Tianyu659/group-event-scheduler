import {
  createRouter,
  createWebHistory,
  RouteLocationRaw,
  RouteRecordRaw,
} from "vue-router";

import Login from "./views/Login.vue";
import Register from "./views/Register.vue";
import Profile from "./views/Profile.vue";
import Dashboard from "./views/Dashboard.vue";
import { session } from "@/session";

function isAuthenticated(
  to: RouteLocationRaw,
  from: RouteLocationRaw,
  next: (route?: unknown) => void
): void {
  if (session.user === null) {
    next({ name: "login" });
  } else {
    next();
  }
}

const routes: Array<RouteRecordRaw> = [
  {
    path: "/",
    name: "dashboard",
    component: Dashboard,
    beforeEnter: isAuthenticated,
  },
  {
    path: "/login/",
    name: "login",
    component: Login,
  },
  {
    path: "/profile/",
    name: "profile",
    component: Profile,
    beforeEnter: isAuthenticated,
  },
  {
    path: "/register/",
    name: "register",
    component: Register,
  },
];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

export default router;
