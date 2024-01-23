package com.example.ande.helpers;

import android.os.CountDownTimer;

public abstract class PauseableCountDownTimer {

    private long millisInFuture;
    private long countDownInterval;
    private long millisRemaining;
    private CountDownTimer countDownTimer;
    private boolean isPaused;

    public PauseableCountDownTimer(long millisInFuture, long countDownInterval) {
        this.millisInFuture = millisInFuture;
        this.countDownInterval = countDownInterval;
        this.millisRemaining = millisInFuture;
        this.isPaused = false;

        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (!isPaused) {
                    millisRemaining = millisUntilFinished;
                    onTickCustom(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                onFinishCustom();
            }
        };
    }

    public abstract void onTickCustom(long millisUntilFinished);

    public abstract void onFinishCustom();

    public void start() {
        countDownTimer.start();
    }

    public void pause() {
        if (!isPaused) {
            countDownTimer.cancel();
            isPaused = true;
        }
    }

    public void resume() {
        if (isPaused) {
            countDownTimer = new CountDownTimer(millisRemaining, countDownInterval) {
                @Override
                public void onTick(long millisUntilFinished) {
                    millisRemaining = millisUntilFinished;
                    onTickCustom(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    onFinishCustom();
                }
            };
            countDownTimer.start();
            isPaused = false;
        }
    }

    public void cancel() {
        countDownTimer.cancel();
    }

    public long getMillisRemaining() {
        return millisRemaining;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
