// setAdminClaim.js
const admin = require("firebase-admin");
admin.initializeApp({
  credential: admin.credential.cert(require("./serviceAccountKey.json")),
});

const uid = "zDmceTS4VSZe1dUe0cpmTR9mO4H2";

admin.auth().setCustomUserClaims(uid, { admin: true })
  .then(() => {
    console.log(`ユーザー(${uid})にadminクレームを付与しました。`);
    process.exit();
  })
  .catch(err => {
    console.error("エラー:", err);
    process.exit(1);
  });