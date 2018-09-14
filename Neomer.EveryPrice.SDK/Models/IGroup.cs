using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Neomer.EveryPrice.SDK.Models
{
    public interface IGroup : IEntity
    {

        string Name { get; set; }

        IGroup Parent { get; set; }

    }
}
