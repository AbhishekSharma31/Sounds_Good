package app.abhishek.com.sounds_good;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by Abhishek on 5/3/15.
 */
public class StatusAdapter extends ArrayAdapter<ParseObject> {


        protected Context mContext;
        protected List mStatus;

        public StatusAdapter(Context context, List status) {
            super(context, R.layout.homepagecustomlayout, status);
            mContext = context;
            mStatus = status;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = LayoutInflater.from(mContext).inflate(
                        R.layout.homepagecustomlayout, null);
                holder = new ViewHolder();
                holder.usernameHomepage = (TextView) convertView
                        .findViewById(R.id.usernameHP);
                holder.statusHomepage = (TextView) convertView
                        .findViewById(R.id.statusHP);

                convertView.setTag(holder);
            } else {

                holder = (ViewHolder) convertView.getTag();

            }

            ParseObject statusObject = (ParseObject) mStatus.get(position);

            // title
            String username = statusObject.getString("user");
            holder.usernameHomepage.setText(username);

            // content
            String status = statusObject.getString("newStatus");
            holder.statusHomepage.setText(status);

            return convertView;
        }

        public static class ViewHolder {
            TextView usernameHomepage;
            TextView statusHomepage;

        }

    }

