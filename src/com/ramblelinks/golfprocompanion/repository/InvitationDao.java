package com.ramblelinks.golfprocompanion.repository;

public interface InvitationDao {

		public int createInvitation(int hostPlayer_id, int rcvPlayer_id, int p_golfcourse_id, String p_playDate, String p_playTime, String p_status, String p_description);
		
		public String updateInvitation(int invitation_id, int player_id, String p_status, String p_description );
		
		
}
