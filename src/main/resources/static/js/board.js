document.addEventListener("DOMContentLoaded", () => {
    const studyLogDialog =
        document.getElementById("studyLogDialog");

    const openStudyLogDialogButton =
        document.getElementById("openStudyLogDialog");

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

    const openStudyLogModal =
        document.body.dataset.openStudyLogModal === "true";

    if (openStudyLogModal) {
        studyLogDialog.showModal();
    }
});