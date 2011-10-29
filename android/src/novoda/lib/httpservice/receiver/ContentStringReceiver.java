package novoda.lib.httpservice.receiver;

import android.os.Handler;
import android.os.Parcel;
import android.os.ResultReceiver;

public class ContentStringReceiver extends ResultReceiver {
	
	private String contentClassSimpleName;
	
	public ContentStringReceiver(Handler handler) {
		super(handler);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("String builder");
		return sb.toString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		super.writeToParcel(dest, flags);
		dest.writeString(String.class.getSimpleName());
	}

	public void setContentClassSimpleName(String contentClassSimpleName) {
		this.contentClassSimpleName = contentClassSimpleName;
	}

	public String getContentClassSimpleName() {
		return contentClassSimpleName;
	}
}
