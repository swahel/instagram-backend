package services.activities.Actions;

import org.json.JSONObject;
import persistence.nosql.ActivityMethods;

public class HandleCommentLikeNotification implements Action {

    public static JSONObject execute(JSONObject jsonObject, String userId) {
        JSONObject params = jsonObject.getJSONObject("params");
		String receiverId = params.getString("receiverId");
		String commentID = params.getString("commentID");

		JSONObject activityJSON = new JSONObject();
		JSONObject innerJSON = new JSONObject();
		innerJSON.put("type", "liking_comment");
		innerJSON.put("comment_id", commentID);
		activityJSON.put("activity_type", innerJSON);
		activityJSON.put("sender_id", userId);
		activityJSON.put("receiver_id", receiverId);
		activityJSON.put("created_at",new java.util.Date());
		activityJSON.put("blocked_at","null");
		activityJSON.put("id",utilities.Main.generateUUID());
		String id = ActivityMethods.insertNotification(activityJSON);

        return new JSONObject().put("id", id);
    }
}

