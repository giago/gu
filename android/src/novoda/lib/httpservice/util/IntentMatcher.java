package novoda.lib.httpservice.util;

import novoda.lib.httpservice.provider.IntentWrapper;
import android.content.Intent;

public class IntentMatcher {
	
	public static final boolean matchByUid(Intent intent1, Intent intent2) {
		long uid1 = intent1.getLongExtra(IntentWrapper.Extra.uid, IntentWrapper.DEFAULT_UID);
		long uid2 = intent2.getLongExtra(IntentWrapper.Extra.uid, IntentWrapper.DEFAULT_UID);
		return (uid1 == uid2);
	}

}
