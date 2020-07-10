package com.webfarmatics.proteckapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.webfarmatics.proteckapp.R;
import com.webfarmatics.proteckapp.model.Issue;
import com.webfarmatics.proteckapp.model.IssueResp;
import com.webfarmatics.proteckapp.utils.CommonUtil;

import java.util.List;

public class IssueListAdapter extends BaseAdapter {

    Context context;
    List<IssueResp> issueList;

    public IssueListAdapter(Context context, List<IssueResp> issueList) {
        this.context = context;
        this.issueList = issueList;
    }

    @Override
    public int getCount() {
        return issueList.size();
    }

    @Override
    public Object getItem(int position) {
        return issueList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View viewOne = convertView;

        if (convertView == null) { // if it's not recycled, initialize some
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewOne = inflater.inflate(R.layout.item_issue_history, parent, false);
        }

        String statusStr = context.getResources().getString(R.string.ac_is_his_sts);
        String dateStr = context.getResources().getString(R.string.ac_is_his_dt);
        String issueStr = context.getResources().getString(R.string.ac_is_his_is);
        String issue = issueList.get(position).getIssueName();
        if (issueList.get(position).getIssueName().equalsIgnoreCase("Other")) {
            issue = context.getResources().getString(R.string.ac_is_his_oth);
        }

        String status;
        if (issueList.get(position).getStatus().equalsIgnoreCase("Complete")) {
            status = context.getResources().getString(R.string.ac_is_his_cm);
        } else {
            status = context.getResources().getString(R.string.ac_is_his_pen);
        }

        TextView tvIssue = viewOne.findViewById(R.id.tvIssue);
        TextView tvIssueStatus = viewOne.findViewById(R.id.tvIssueStatus);
        TextView tvIssueDate = viewOne.findViewById(R.id.tvIssueDate);
        tvIssueStatus.setText(statusStr + " : " + status);
        tvIssue.setText(issueStr + " : " + issue);
        tvIssueDate.setText(dateStr + " : " + CommonUtil.getDateToDisplay(issueList.get(position).getCreatedDate()));

        return viewOne;
    }
}
