package com.webwonder.atm.services.paperStorage;

public abstract class BaseNoteStorage {
    protected Integer total;
    protected Integer captured = 0;

    public BaseNoteStorage(Integer total) {
        this.total = total;
    }

    public Integer captureMaximum(int count) {
        Integer available = total - captured;

        if (available < count) {
            captured += available;
            return available;
        }

        captured += count;
        return count;
    }

    public void complete(Integer singleCapture) {
        total -= singleCapture;
        captured -= singleCapture;
    }

    public void abort(Integer singleCapture) {
        captured -= singleCapture;
    }

    public Integer getTotal(){
        return total;
    }

    public Integer getCaptured() {
        return captured;
    }
}
