// HTMLの読み込みが終わってから、中の処理を実行する
document.addEventListener("DOMContentLoaded", () => {
    // HTMLの <dialog id="studyLogDialog"> を取得
    const studyLogDialog =
        document.getElementById("studyLogDialog");

    // HTMLの <button id="openStudyLogDialog" type="button"> を取得
    const openStudyLogDialogButton =
        document.getElementById("openStudyLogDialog");

    // HTMLの <button id="closeStudyLogDialog" type="button"> を取得
    const closeStudyLogDialogButton =
        document.getElementById("closeStudyLogDialog");

    if (!studyLogDialog) {
        return;
    }

    if (openStudyLogDialogButton) {
        openStudyLogDialogButton.addEventListener("click", () => {
            studyLogDialog.showModal();
        });
    }

    if (closeStudyLogDialogButton) {
        closeStudyLogDialogButton.addEventListener("click", () => {
            studyLogDialog.close();
        });
    }

    // HTMLの th:data-open-study-log-modal が発火(true)したらopenStudyLogModalにtrueが入り、
    // studyLogDialog.showModal()が宣言されてポップアップが表示され続ける。
    const openStudyLogModal =
        document.body.dataset.openStudyLogModal === "true";

    if (openStudyLogModal) {
        studyLogDialog.showModal();
    }
});