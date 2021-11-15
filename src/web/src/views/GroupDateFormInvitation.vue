<template>
  <div>
    Invited {{ invitation.user.firstName }} {{ invitation.user.lastName }} ({{
      invitation.user.username
    }})
  </div>
</template>

<script lang="ts">
import { Options, Vue } from "vue-class-component";
import { Prop } from "vue-property-decorator";
import { Invitation } from "@/models/groupDate";

@Options({})
export default class GroupDateFormInvitation extends Vue {
  @Prop() public invitation!: Invitation;
}
</script>

<style scoped></style>
