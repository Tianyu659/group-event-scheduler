<template>
  <div class="form">
    <div class="group">
      <input
        id="name"
        type="text"
        placeholder="username"
        v-model="groupDate.name"
      />
      <label>Name</label>
    </div>
    <div class="group">
      <input
        id="description"
        type="text"
        placeholder="description"
        v-model="groupDate.description"
      />
      <label>Description</label>
    </div>
    <div>
      <h3>Events</h3>
      <group-date-form-event
        v-for="groupDateEvent of groupDate.events"
        :group-date-event="groupDateEvent"
        :key="groupDateEvent"
      />
      <div v-if="groupDate.events.length === 0">
        You haven't added any events!
      </div>
      <group-date-form-event-form :group-date="groupDate" />
    </div>
    <div>
      <h3>Invitations</h3>
      <group-date-form-invitation
        v-for="invitation of groupDate.invitations"
        :invitation="invitation"
        :key="invitation"
      />
      <div v-if="groupDate.invitations.length === 0">
        You haven't invited anyone!
      </div>
      <group-date-form-invitation-form :group-date="groupDate" />
    </div>
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { session } from "@/session";
import { GroupDate } from "@/models/groupDate";
import GroupDateFormEvent from "@/views/GroupDateFormEvent.vue";
import GroupDateFormEventForm from "@/views/GroupDateFormEventForm.vue";
import GroupDateFormInvitationForm from "@/views/GroupDateFormInvitationForm.vue";
import GroupDateFormInvitation from "@/views/GroupDateFormInvitation.vue";

@Options({
  components: {
    GroupDateFormInvitation,
    GroupDateFormInvitationForm,
    GroupDateFormEventForm,
    GroupDateFormEvent,
  },
})
export default class GroupDateForm extends Vue {
  public groupDate: GroupDate = GroupDate.empty(session.user!);
}
</script>

<style scoped></style>
