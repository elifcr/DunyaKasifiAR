using UnityEngine;
using UnityEngine.SceneManagement;

public class StartGame : MonoBehaviour
{
    public void LoadAvatarScene()
    {
        SceneManager.LoadScene("AvatarSelect");
    }
}