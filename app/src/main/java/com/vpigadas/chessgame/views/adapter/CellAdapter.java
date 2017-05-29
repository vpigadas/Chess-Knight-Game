package com.vpigadas.chessgame.views.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vpigadas.chessgame.MainActivity;
import com.vpigadas.chessgame.R;
import com.vpigadas.chessgame.utils.Solution;
import com.vpigadas.chessgame.views.custom.Cell;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vpigadas on 28/05/2017.
 */

public class CellAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int BOARD_CELLS = 64;

    private final String SUG_POS_HORIZONTAL_TOP_LEFT = "horizontalTopLeft";
    private final String SUG_POS_HORIZONTAL_TOP_RIGHT = "horizontalTopRight";
    private final String SUG_POS_HORIZONTAL_BOTTOM_LEFT = "horizontalBottomLeft";
    private final String SUG_POS_HORIZONTAL_BOTTOM_RIGHT = "horizontalBottomRight";
    private final String SUG_POS_VERTICAL_TOP_LEFT = "verticalTopLeft";
    private final String SUG_POS_VERTICAL_TOP_RIGHT = "verticalTopRight";
    private final String SUG_POS_VERTICAL_BOTTOM_LEFT = "verticalBottomLeft";
    private final String SUG_POS_VERTICAL_BOTTOM_RIGHT = "verticalBottomRight";

    private Context context;

    private AtomicInteger startPoint = new AtomicInteger(-1);
    private AtomicInteger endPoint = new AtomicInteger(-1);
    private ArrayList<Integer> selectedPosition = new ArrayList<>();

    public CellAdapter(Context context) {
        this.context = context;
        this.startPoint = new AtomicInteger(-1);
        this.endPoint = new AtomicInteger(-1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_cell, parent, false);
        return new CellView(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof CellView) {
            ((CellView) holder).init(position);
        }
    }

    @Override
    public int getItemCount() {
        return BOARD_CELLS;
    }

    private HashMap<String, Integer> calculateAvailableMoves(int position) {
        HashMap<String, Integer> suggestedPosition = new HashMap<>();
        HashMap<String, Integer> hashHorizontal = calcAvailMovHorizontal(position);
        HashMap<String, Integer> hashVertical = calcAvailMovVertical(position);

        suggestedPosition.putAll(hashHorizontal);
        suggestedPosition.putAll(hashVertical);

        return suggestedPosition;
    }

    private int calcAxeY(int position) {
        return position % 8;
    }

    private HashMap<String, Integer> calcAvailMovHorizontal(int position) {
        HashMap<String, Integer> hashMap = new HashMap<>();

        int posY = calcAxeY(position);

        int sugPosTopLeft = position - (8 + 2);
        int sugPosTopRight = position - (8 - 2);
        int sugPosBottomLeft = position + (8 - 2);
        int sugPosBottomRight = position + (8 + 2);

        if (sugPosTopLeft > -1 && sugPosTopLeft < BOARD_CELLS && posY > 1) {
            hashMap.put(SUG_POS_HORIZONTAL_TOP_LEFT, sugPosTopLeft);
        }

        if (sugPosBottomLeft > -1 && sugPosBottomLeft < BOARD_CELLS && posY > 1) {
            hashMap.put(SUG_POS_HORIZONTAL_BOTTOM_LEFT, sugPosBottomLeft);
        }

        if (sugPosTopRight > -1 && sugPosTopRight < BOARD_CELLS && posY < 6) {
            hashMap.put(SUG_POS_HORIZONTAL_TOP_RIGHT, sugPosTopRight);
        }

        if (sugPosBottomRight > -1 && sugPosBottomRight < BOARD_CELLS && posY < 6) {
            hashMap.put(SUG_POS_HORIZONTAL_BOTTOM_RIGHT, sugPosBottomRight);
        }

        return hashMap;
    }

    private HashMap<String, Integer> calcAvailMovVertical(int position) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        int posY = calcAxeY(position);

        int sugPosTopLeft = position - (2 * 8) - 1;
        int sugPosTopRight = position - (2 * 8) + 1;
        int sugPosBottomLeft = position + (2 * 8) - 1;
        int sugPosBottomRight = position + (2 * 8) + 1;

        if (sugPosTopLeft > -1 && sugPosTopLeft < BOARD_CELLS && posY != 0) {
            hashMap.put(SUG_POS_VERTICAL_TOP_LEFT, sugPosTopLeft);
        }

        if (sugPosBottomLeft > -1 && sugPosBottomLeft < BOARD_CELLS && posY != 0) {
            hashMap.put(SUG_POS_VERTICAL_BOTTOM_LEFT, sugPosBottomLeft);
        }

        if (sugPosTopRight > -1 && sugPosTopRight < BOARD_CELLS && posY != 7) {
            hashMap.put(SUG_POS_VERTICAL_TOP_RIGHT, sugPosTopRight);
        }

        if (sugPosBottomRight > -1 && sugPosBottomRight < BOARD_CELLS && posY != 7) {
            hashMap.put(SUG_POS_VERTICAL_BOTTOM_RIGHT, sugPosBottomRight);
        }

        return hashMap;
    }

    private ArrayList<Solution> findASolution(HashMap<String, Integer> availableMoves) {
        ArrayList<Solution> solutionsSet = new ArrayList<>();

        StringBuilder strMoves;
        for (Integer position : availableMoves.values()) {
            strMoves = new StringBuilder().append(position).append("-");
            if (position == endPoint.get()) {
                strMoves.append("success");

                Solution solution = new Solution(position);
                solutionsSet.add(solution);
                break;
            } else {
                HashMap<String, Integer> roundOne = calculateAvailableMoves(position);
                for (Integer _position : roundOne.values()) {
                    if (_position == endPoint.get()) {
                        strMoves.append(_position).append("-");
                        strMoves.append("success");

                        Solution solution = new Solution(position);
                        solution.setPositionTwo(_position);
                        solutionsSet.add(solution);
                        break;
                    } else {
                        HashMap<String, Integer> roundTwo = calculateAvailableMoves(_position);
                        for (Integer __position : roundTwo.values()) {
                            if (__position == endPoint.get()) {
                                strMoves.append(__position).append("-");
                                strMoves.append("success");

                                Solution solution = new Solution(position);
                                solution.setPositionTwo(_position);
                                solution.setPositionThree(__position);
                                solutionsSet.add(solution);

                                break;
                            }
                        }
                    }
                }
            }

            Log.d(CellAdapter.class.getSimpleName(), strMoves.toString());
        }

        return solutionsSet;
    }

    public void startDrawingSolution(Solution solution) {
        drawRoute(startPoint.get(), solution.getPositionOne());
        drawRoute(solution.getPositionOne(), solution.getPositionTwo());
        drawRoute(solution.getPositionTwo(), solution.getPositionThree());
    }

    private void drawRoute(int currentPosition, int nextPosition) {
        boolean found = false;
        HashMap<String, Integer> movesHorizontal = calcAvailMovHorizontal(currentPosition);


        for (String key : movesHorizontal.keySet()) {
            if (movesHorizontal.get(key).equals(nextPosition)) {
                found = true;
                drawCell(currentPosition, key);
                break;
            }
        }

        if (!found) {
            HashMap<String, Integer> movesVertical = calcAvailMovVertical(currentPosition);
            for (String key : movesVertical.keySet()) {
                if (movesVertical.get(key).equals(nextPosition)) {
                    drawCell(currentPosition, key);
                    break;
                }
            }
        }

    }

    private void drawCell(int position, String type) {
        if (SUG_POS_HORIZONTAL_TOP_LEFT.equals(type)) {
            selectedPosition.add(position - 1);
            notifyItemChanged(position - 1);
            selectedPosition.add(position - 2);
            notifyItemChanged(position - 2);
            selectedPosition.add(position - 2 - 8);
            notifyItemChanged(position - 2 - 8);
        } else if (SUG_POS_HORIZONTAL_TOP_RIGHT.equals(type)) {
            selectedPosition.add(position + 1);
            notifyItemChanged(position + 1);
            selectedPosition.add(position + 2);
            notifyItemChanged(position + 2);
            selectedPosition.add(position + 2 - 8);
            notifyItemChanged(position + 2 - 8);
        } else if (SUG_POS_HORIZONTAL_BOTTOM_LEFT.equals(type)) {
            selectedPosition.add(position - 1);
            notifyItemChanged(position - 1);
            selectedPosition.add(position - 2);
            notifyItemChanged(position - 2);
            selectedPosition.add(position - 2 + 8);
            notifyItemChanged(position - 2 + 8);
        } else if (SUG_POS_HORIZONTAL_BOTTOM_RIGHT.equals(type)) {
            selectedPosition.add(position + 1);
            notifyItemChanged(position + 1);
            selectedPosition.add(position + 2);
            notifyItemChanged(position + 2);
            selectedPosition.add(position + 2 + 8);
            notifyItemChanged(position + 2 + 8);
        } else if (SUG_POS_VERTICAL_TOP_LEFT.equals(type)) {
            selectedPosition.add(position - 8);
            notifyItemChanged(position - 8);
            selectedPosition.add(position - (2 * 8));
            notifyItemChanged(position - (2 * 8));
            selectedPosition.add(position - (2 * 8) - 1);
            notifyItemChanged(position - (2 * 8) - 1);
        } else if (SUG_POS_VERTICAL_TOP_RIGHT.equals(type)) {
            selectedPosition.add(position - 8);
            notifyItemChanged(position - 8);
            selectedPosition.add(position - (2 * 8));
            notifyItemChanged(position - (2 * 8));
            selectedPosition.add(position - (2 * 8) + 1);
            notifyItemChanged(position - (2 * 8) + 1);
        } else if (SUG_POS_VERTICAL_BOTTOM_LEFT.equals(type)) {
            selectedPosition.add(position + 8);
            notifyItemChanged(position + 8);
            selectedPosition.add(position + (2 * 8));
            notifyItemChanged(position + (2 * 8));
            selectedPosition.add(position + (2 * 8) - 1);
            notifyItemChanged(position + (2 * 8) - 1);
        } else if (SUG_POS_VERTICAL_BOTTOM_RIGHT.equals(type)) {
            selectedPosition.add(position + 8);
            notifyItemChanged(position + 8);
            selectedPosition.add(position + (2 * 8));
            notifyItemChanged(position + (2 * 8));
            selectedPosition.add(position + (2 * 8) + 1);
            notifyItemChanged(position + (2 * 8) + 1);
        }
    }

    class CellView extends RecyclerView.ViewHolder {

        @BindView(R.id.cell)
        Cell imageView;

        CellView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void init(int _position) {
            int position = _position / 8;
            switch (position) {
                case 1:
                case 3:
                case 5:
                case 7: {
                    if (_position % 2 == 0) {
                        imageView.setBackgroundResource(android.R.color.white);
                        imageView.setTextColor(Color.BLACK);
                    } else {
                        imageView.setBackgroundResource(android.R.color.black);
                        imageView.setTextColor(Color.WHITE);
                    }
                }
                break;
                default: {
                    if (_position % 2 == 0) {
                        imageView.setBackgroundResource(android.R.color.black);
                        imageView.setTextColor(Color.WHITE);
                    } else {
                        imageView.setBackgroundResource(android.R.color.white);
                        imageView.setTextColor(Color.BLACK);
                    }
                }
            }

            if (selectedPosition.contains(_position)) {
                imageView.setBackgroundResource(R.color.colorPrimary);
            }

            if (startPoint.get() == _position) {
                imageView.setBackgroundResource(R.color.startPoint);
                imageView.setBackgroundResource(android.R.color.black);
            }

            if (endPoint.get() == _position) {
                imageView.setBackgroundResource(R.color.endPoint);
            }
        }

        @OnClick(R.id.cell)
        public void onClick(View view) {
            int _position = getAdapterPosition();

            int intStart = startPoint.get();
            int intEnd = endPoint.get();
            if (intStart == -1) {
                startPoint.set(_position);
                imageView.setBackgroundResource(R.color.startPoint);

                imageView.setText(String.valueOf((char) 0x265E));
                imageView.setTextColor(Color.BLACK);

                if (context instanceof MainActivity) {
                    ((MainActivity) context).setGameTitle(context.getString(R.string.title_end_game));
                }

            } else if (intEnd == -1) {
                endPoint.set(_position);
                imageView.setBackgroundResource(R.color.endPoint);

                ArrayList<Solution> foundSolution = findASolution(calculateAvailableMoves(intStart));


                if (context instanceof MainActivity) {
                    if (foundSolution.isEmpty()) {
                        ((MainActivity) context).setGameTitle(context.getString(R.string.title_not_found));
                    } else {
                        ((MainActivity) context).setGameTitle(context.getString(R.string.title_results, foundSolution.size()));
                        ((MainActivity) context).setResults(foundSolution);
                    }
                }
            }
        }
    }

}
