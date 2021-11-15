<template>
  <div class="event">
    <h4>{{ groupDateEvent.name }}</h4>
    <p>{{ groupDateEvent.description }}</p>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { GroupDateEvent } from "@/models/groupDate";

@Options({})
export default class GroupDateFormEvent extends Vue {
  @Prop() public groupDateEvent!: GroupDateEvent;
}
</script>

<style scoped></style>
