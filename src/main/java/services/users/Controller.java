package services.users;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.sql.users.User;
import java.io.IOException;
import java.util.List;

import static persistence.sql.Main.closeConnection;
import static persistence.sql.Main.openConnection;
import static persistence.sql.users.Main.getUsersByIds;
import static persistence.sql.users.Main.getUsersIdsByUsernames;


public class Controller extends shared.mq_server.Controller {


    public Controller() {
        super();
    }

    @Override
    public JSONObject execute(JSONObject jsonObject, String userId) throws Exception {
        //TODO: @MAGDY Find a better way of opening and closing db connection
        openConnection();

        String methodName = jsonObject.getString("method");
        JSONObject paramsObject = jsonObject.getJSONObject("params");
        JSONObject resJSON = new JSONObject();
        switch (methodName) {
            case "signUp":
                resJSON = Authentication.SignUp(paramsObject);
                break;
            case "getUserInfo":
                resJSON = Authentication.GetUserInfo(paramsObject, userId);
                break;
            case "createFollow":
                resJSON = UserActions.CreateFollow(paramsObject, userId);
                break;
            case "udpateProfile":
                resJSON = UserActions.UpdateProfile(paramsObject, userId);
                break;
            case "createUnfollow":
                resJSON = UserActions.CreateUnfollow(paramsObject, userId);
                break;
            case "deleteUser":
                resJSON = UserActions.DeleteUser(paramsObject, userId);
                break;
            case "authorizedToView":
                resJSON = Authentication.authorizedToView(paramsObject.getString("viewerId"), paramsObject.getString("toBeViewedId"));
                break;
            case "getUsersByIds":
                // TODO @maged: Refactor logic into dedicated file
                List<User> users = getUsersByIds(
                        new String[] {} // TODO @magdy: Swap with data from params object
                );
                resJSON = new JSONObject()
                        .put("response", new JSONObject().put("data", new JSONArray(users)));
                break;
            case "getUsersIdsByUsernames":
                // TODO @maged: Refactor logic into dedicated file
                List<String> ids = getUsersIdsByUsernames(
                        new String[] {} // TODO @magdy: Swap with data from params object
                );
                resJSON = new JSONObject()
                        .put("response", new JSONObject().put("data", new JSONArray(ids)));
                break;
        }
        closeConnection();
        return resJSON;
    }
}
