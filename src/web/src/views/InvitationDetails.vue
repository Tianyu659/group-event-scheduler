<template>
  <div>
    <div v-if="invitation !== null && response !== null">
      <h1>
        Invite:
        {{ invitation.groupDate.name }}
      </h1>
      <p>{{ invitation.groupDate.description }}</p>
      <p>
        {{ invitation.groupDate.invitationCount[0] }}/{{
          invitation.groupDate.invitationCount[1]
        }}
        responded
      </p>
      <button @click="onClickDecline()">Decline</button>
      <div class="form">
        <h2>Event responses</h2>
        <invitation-event-form
          v-for="event of response.events"
          :group-date="invitation.groupDate"
          :invitation-event-response="event"
          :key="event.event.id"
        />
        <button @click="onClickAccept">Accept</button>
      </div>
    </div>
    <div v-else>Loading...</div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { url } from "@/url";
import { Invitation, InvitationResponse } from "@/models/groupDate";
import InvitationEventForm from "@/views/InvitationEventForm.vue";

@Options({
  components: { InvitationEventForm },
})
export default class Home extends Vue {
  public readonly session = session;
  public invitation: Invitation | null = null;
  public response: InvitationResponse | null = null;

  public created(): void {
    const id = this.$route.params["id"];
    fetch(url(`/invitations/${id}`), {
      method: "GET",
      headers: { Authorization: session.token! },
    }).then((response: Response) => {
      response.json().then((data: Record<string, any>) => {
        this.invitation = Invitation.wrap(data);
        this.response = InvitationResponse.create(this.invitation);
      });
    });
  }

  public onClickAccept(): void {
    const id = this.$route.params["id"];
    fetch(url(`/invitations/${id}/responses/`), {
      method: "POST",
      headers: { Authorization: session.token! },
      body: JSON.stringify(this.response!.dump()),
    }).then((response: Response) => {
      if (response.status === 201) {
        this.$router.push({ name: "dashboard" });
      } else {
        response.json().then((data: Record<string, any>) => {
          console.error(data);
        });
      }
    });
  }

  public onClickDecline(): void {
    const id = this.$route.params["id"];
    fetch(url(`/invitations/${id}/responses/`), {
      method: "POST",
      headers: { Authorization: session.token! },
      body: JSON.stringify(new InvitationResponse(false, [])),
    }).then((response: Response) => {
      if (response.status === 201) {
        this.$router.push({ name: "dashboard" });
      } else {
        response.json().then((data: Record<string, any>) => {
          console.error(data);
        });
      }
    });
  }
}
</script>

<style scoped></style>
