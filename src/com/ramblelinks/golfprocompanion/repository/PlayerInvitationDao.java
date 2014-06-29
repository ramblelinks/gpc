package com.ramblelinks.golfprocompanion.repository;

import java.util.List;

import com.ramblelinks.golfprocompanion.domain.PlayerInvitation;

public interface PlayerInvitationDao {
	
	public List<PlayerInvitation> getInvitations(int p_player_id);

    public List<PlayerInvitation> getSentInvitations(int p_player_id);
    
    public List<PlayerInvitation> getAcceptedExpiredInvitations(int p_player_id);
    
}
