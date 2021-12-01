<template>
  <div class="invitation">
    <h3>
      <span>
        Invitation to {{ invitation.user.firstName }}
        {{ invitation.user.lastName }}</span
      >
      <span class="float-right cursor-pointer" @click="onClickDelete">
        &#x2715;
      </span>
    </h3>
    <div v-if="invitation.response">
      <ul class="fancy" v-if="invitation.response.accepted">
        <li v-for="event of invitation.response.events" :key="event">
          {{ event.event.name }}:
          <span v-if="event.available">
            Available, interest {{ event.interest }}/5
          </span>
          <span v-else>Unavailable</span>
        </li>
      </ul>
      <p v-else>User is unavailable!</p>
    </div>
    <div v-else>No response!</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { GroupDate, Invitation } from "@/models/groupDate";
import GroupDateFormEvent from "@/views/GroupDateFormEvent.vue";
import { Prop } from "vue-property-decorator";
import { url } from "@/url";
import router from "@/router";

@Options({
  components: { GroupDateFormEvent },
})
export default class Home extends Vue {
  public readonly session = session;
  @Prop() public groupDate!: GroupDate;
  @Prop() public invitation!: Invitation;
  @Prop() public invitations!: Array<Invitation>;

  public onClickDelete(): void {
    if (this.invitations.length === 1) {
      const result = window.confirm(
        "This is the last invitation. Do you wish to delete the group date?"
      );
      if (result) {
        fetch(url(`/groupDates/${this.groupDate.id}`), {
          method: "DELETE",
          headers: { Authorization: session.token! },
        }).then((response: Response) => {
          if (response.status === 204) {
            router.push({ name: "dashboard" });
          }
        });
      }
    } else {
      const result = window.confirm(
        `Delete invitation to ${this.invitation.user.firstName} ${this.invitation.user.lastName}?`
      );
      if (result) {
        fetch(
          url(
            `/groupDates/${this.groupDate.id}/invitations/${this.invitation.id}`
          ),
          {
            method: "DELETE",
            headers: { Authorization: session.token! },
          }
        ).then((response: Response) => {
          if (response.status === 204) {
            this.$emit("delete");
          } else {
            response.json().then((data: any) => console.error(data));
          }
        });
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.invitation {
  border: 1px solid lightgray;
  padding: 1em;
  border-radius: 0.25rem;

  .location {
    font-weight: bold;
  }

  & > *:first-child {
    margin-top: 0;
  }

  & > *:last-child {
    margin-bottom: 0;
  }

  margin-bottom: 1rem;
}
</style>
