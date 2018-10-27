using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Core
{
	public class Singleton<T> : Singleton<T, T>
		where T : class
	{
	}

	public class Singleton<TClass, TInterface> 
		where TClass : class, TInterface
    {
        private static TInterface _instance;

        protected Singleton()
        {
        }

        private static TInterface CreateInstance()
        {
            ConstructorInfo cInfo = typeof(TClass).GetConstructor(
                BindingFlags.Instance | BindingFlags.NonPublic,
                null,
                new Type[0],
                new ParameterModifier[0]);

			if (cInfo == null)
			{
				throw new Exception("No default constructor!");
			}

            return (TInterface)cInfo.Invoke(null);
        }

        public static TInterface Instance
        {
            get
            {
                if (_instance == null)
                {
                    _instance = CreateInstance();
                }

                return _instance;
            }
        }
    }
}
