import librosa
import numpy as np
import soundfile  # 读取和写入音频文件
from numba import float64

# https://data-flair.training/blogs/python-mini-project-speech-emotion-recognition/

def extract_feature(file_name, mfcc, chroma, mel):
    with soundfile.SoundFile(file_name) as sound_file:
        x = sound_file.read(dtype=float64)
        sample_rate = sound_file.samplerate
        if chroma:
            stft = np.abs(librosa.stft(x))
        result = np.array([])
        if mfcc:
            mfccs = np.abs(librosa.feature.mfcc(y=x, sr=sample_rate, n_mfcc=40).T, axis=0)
            result = np.hstack((result, mfccs))
        if chroma:
            chroma = np.mean(librosa.feature.chroma_stft(S=stft, sr=sample_rate).T, axis=0)
            result = np.hstack((result, chroma))
        if mel:
            mel = np.mean(librosa.feature.melspectrogram(x, sr=sample_rate).T, axis=0)
            result = np.hstack((result, mel))
    return result

# dictionary



# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    """"""
