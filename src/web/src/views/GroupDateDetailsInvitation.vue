<template>
  <div>
    <h3>
      Invitation to {{ invitation.user.firstName }}
      {{ invitation.user.lastName }}
    </h3>
    <div v-if="invitation.response">
      <ul class="fancy">
        <li v-for="event of invitation.response.events" :key="event">
          {{ event.event.name }}:
          <span v-if="event.available">
            Available, interest {{ event.interest }}/5
          </span>
          <span v-else>Unavailable</span>
        </li>
      </ul>
    </div>
    <div v-else>No response!</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { Invitation } from "@/models/groupDate";
import GroupDateFormEvent from "@/views/GroupDateFormEvent.vue";
import { Prop } from "vue-property-decorator";

@Options({
  components: { GroupDateFormEvent },
})
export default class Home extends Vue {
  public readonly session = session;
  @Prop() public invitation!: Invitation;
}
</script>

<style lang="scss" scoped></style>
