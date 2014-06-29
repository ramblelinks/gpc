package com.ramblelinks.golfprocompanion.repository;

import com.ramblelinks.golfprocompanion.domain.Player;
import com.ramblelinks.golfprocompanion.view.PlayerProfile;
import com.ramblelinks.golfprocompanion.view.RegisterPlayer;

public interface PlayerDao {
	
		public int validateEmail(String p_email );
		
		public int validateUserName(String p_user ); 

		public int resetPassword(String username, String password);
		
		public String addPlayer(RegisterPlayer registerPlayer);
		
		public Player getPlayer(String username, String password);
		
		public int setPlayerRating(int reviewer_id, int reviweee_id, int rating, String comments);
		
		public Player getPlayerInfo(int p_player_id);
		
		public int updatePlayerInfo(Player updatePlayer);
				
}
