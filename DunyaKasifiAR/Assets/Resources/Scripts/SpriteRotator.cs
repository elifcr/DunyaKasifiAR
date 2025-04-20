using UnityEngine;
using UnityEngine.UI;

public class SpriteRotator : MonoBehaviour
{
    public Sprite[] characterAngles;
    public Image characterImage;
    private int currentIndex = 0;

    public void RotateLeft()
    {
        currentIndex = (currentIndex - 1 + characterAngles.Length) % characterAngles.Length;
        characterImage.sprite = characterAngles[currentIndex];
    }

    public void RotateRight()
    {
        currentIndex = (currentIndex + 1) % characterAngles.Length;
        characterImage.sprite = characterAngles[currentIndex];
    }
}
